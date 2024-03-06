package com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.data.TodoAreaData
import com.c7z.mappilogue_aos.data.remote.request.RequestAddTodo
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.data.remote.response.ResponseScheduleDetailData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import com.c7z.mappilogue_aos.presentation.util.FailureResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddTodoViewModel @Inject constructor(private val scheduleRepository: ScheduleRepository) :
    ViewModel() {
    private val _scheduleId = MutableLiveData<Int>()
    val scheduleId : LiveData<Int> = _scheduleId

    var todoTitle = MutableLiveData<String>().apply { value = "" }

    private val _colorVisibility = MutableLiveData<Boolean>(false)
    val colorVisibility: LiveData<Boolean> = _colorVisibility

    private val _colorData = MutableLiveData<List<ResponseTodoColor.ResultTodoColor>>()
    val colorData: LiveData<List<ResponseTodoColor.ResultTodoColor>> = _colorData

    private val _selectedColor = MutableLiveData<ResponseTodoColor.ResultTodoColor>()
    val selectedColor: LiveData<ResponseTodoColor.ResultTodoColor> = _selectedColor

    private val _selectedDates = MutableLiveData<List<String>>()
    val selectedDates: LiveData<List<String>> = _selectedDates

    private val _startDate = MutableLiveData<LocalDate>()
    val startDate: LiveData<LocalDate> = _startDate

    private val _endDate = MutableLiveData<LocalDate>()
    val endDate: LiveData<LocalDate> = _endDate

    private val _checkStatus = MutableLiveData<Boolean>(false)
    val checkStatus: LiveData<Boolean> = _checkStatus

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private var _locationWithDate =
        mutableMapOf<String, MutableList<ResponseKakaoLocation.Document>>()
    var locationWithDate : Map<String, MutableList<ResponseKakaoLocation.Document>> = _locationWithDate

    private val _locationList = MutableLiveData<MutableList<ResponseKakaoLocation.Document>>()
    val locationList: LiveData<MutableList<ResponseKakaoLocation.Document>> = _locationList
    private var toolsLocationList = mutableListOf<ResponseKakaoLocation.Document>()

    private var _checkedLocationList = mutableListOf<Int>()
    val checkedLocationList: List<Int> = _checkedLocationList

    private var _todoAlarmList = MutableLiveData<MutableList<String>>()
    val todoAlarmList: LiveData<MutableList<String>> = _todoAlarmList

    private var _todoUploadState = MutableLiveData<Int>()
    val todoUploadState : LiveData<Int> = _todoUploadState

    fun setScheduleId(scheduleId: Int) {
        _scheduleId.value = scheduleId
    }

    fun changeColorListVisibility() {
        _colorVisibility.value = _colorVisibility.value?.not()
    }

    fun setSelectedColor(item: ResponseTodoColor.ResultTodoColor) {
        _selectedColor.value = item
    }

    fun setStartDate(date: LocalDate) {
        _startDate.value = date
        initLocationWithDate()
    }

    fun setEndDate(date: LocalDate) {
        _endDate.value = date
        initLocationWithDate()
    }

    fun setSelectedDates(dates: List<String>) {
        _selectedDates.value = dates

    }

    fun changeCheckStatus() {
        _checkStatus.value = _checkStatus.value?.not()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun insertLocationList(document: ResponseKakaoLocation.Document) {
        toolsLocationList.add(document)
        notifyLocationList()
    }

    fun removeLocationList(position: Int) {
        toolsLocationList.removeAt(position)
        notifyLocationList()
    }

    private fun notifyLocationList() {
        _locationList.value = toolsLocationList
        if (toolsLocationList.isEmpty()) _checkStatus.value = false
    }

    fun initLocationWithDate() {
        _locationWithDate = mutableMapOf()
    }

    fun setLocationWithDate(locationList: MutableList<ResponseKakaoLocation.Document>) {
        _locationWithDate[selectedDate.value!!] = locationList
    }

    fun onChangeSelectedDate() {
        toolsLocationList = _locationWithDate[selectedDate.value!!] ?: mutableListOf()
        notifyLocationList()
    }

    fun appendCheckedLocationList(position: Int) {
        _checkedLocationList.add(position)
    }

    fun removeCheckedLocationList(position: Int) {
        _checkedLocationList.remove(position)
    }

    fun initCheckedLocationList() {
        _checkedLocationList.clear()
    }

    fun initTodoAlarmList() {
        _todoAlarmList.value = mutableListOf()
    }

    fun setTodoAlarmList(alarmList: List<String>) {
        _todoAlarmList.value = alarmList.toMutableList()
    }

    fun requestScheduleColorData() {
        viewModelScope.launch {
            scheduleRepository.requestScheduleColorData()
                .onSuccess { _colorData.value = it }
        }
    }

    fun requestSaveTodo() {
        if(scheduleId.value == null) postTodo()
        else modifyTodo(scheduleId.value!!)
    }

    private fun postTodo() {
        transformAlarmData()
        viewModelScope.launch {
            scheduleRepository.requestAddTodo(
                mapToRequestType()
            )
                .onSuccess { _todoUploadState.value = it.code }
                .onFailure { _todoUploadState.value = it.message?.convertAndGetCode()?.code }
        }
    }

    private fun modifyTodo(scheduleId: Int) {
        transformAlarmData()
        viewModelScope.launch {
            scheduleRepository.requestModifyDetailDateSchedule(scheduleId,
                mapToRequestType())
                .also { Log.e("----", "modifyTodo: ${mapToRequestType()}", ) }
                .onSuccess { _todoUploadState.value = it }
                .onFailure {
                    Log.e("----", "modifyTodo: ${it.message}", )
                    _todoUploadState.value = Gson().fromJson(it.message, FailureResponse::class.java).errorCode.toInt() }
        }
    }

    private fun mapToRequestType() : RequestAddTodo {
        return RequestAddTodo(
            todoTitle.value ?: "",
            selectedColor.value?.id ?: (Random.nextInt(15) + 1),
            startDate.value.toString(),
            endDate.value.toString(),
            _todoAlarmList.value ?: listOf(),
            _locationWithDate.mapToArea()
        )
    }

    private fun transformAlarmData() {
        _todoAlarmList.value = _todoAlarmList.value?.map { it.mapToISOTime() }?.toMutableList()
    }

    private fun String.mapToISOTime() : String {
        return if (this.contains("T")) this
        else {
            val arr = this.split(" ")
            "${arr[0]}T${arr[1].convertTo24Hours(arr[2] == "AM")}:00"
        }
    }

    private fun String.convertTo24Hours(isAm : Boolean) : String {
        return "${String.format("%02d", this.split(":")[0].toInt() + if(!isAm) 12 else 0)}:${this.split(":")[1]}"
    }

    private fun MutableMap<String, MutableList<ResponseKakaoLocation.Document>>.mapToArea(): MutableList<TodoAreaData.AreaWithDate> {
        return this.toList().map { TodoAreaData.AreaWithDate(it.first.convertToDateFormat(), it.second.hi()) }.toMutableList()
    }

    private fun String.convertToDateFormat() : String {
        return this.replace("년", "-").replace("월", "-").replace("일", "-").replace(" ", "").convertToLocalDate().toString()
    }

    private fun String.convertToYearMonthDate() : String {
        return "${this.split("-")[0]}년 ${this.split("-")[1].toInt()}월 ${this.split("-")[2]}일"
    }

    private fun String.convertToLocalDate() : LocalDate {
        return LocalDate.of(this.split("-")[0].toInt(), this.split("-")[1].toInt(), this.split("-")[2].toInt())
    }

    private fun List<ResponseKakaoLocation.Document>.hi(): MutableList<TodoAreaData.AreaData> {
        return this.map { TodoAreaData.AreaData(it.place_name, it.road_address_name, it.y, it.x, if(it.time == "설정 안 함") null else it.time) }
            .toMutableList()
    }

    fun requestTodoDetailData(scheduleId : Int) {
        viewModelScope.launch {
            scheduleRepository.requestGetDetailDateSchedule(scheduleId)
                .onSuccess { it.setModifyData()
                    Log.e("----", "requestTodoDetailData: $it", )}
                .onFailure { Log.e("----", "requestTodoDetailData: ${it.message}", ) }
        }
    }

    private fun ResponseScheduleDetailData.ResultScheduleDetailData.setModifyData() {
        todoTitle.value = if(this.scheduleBaseInfo.title != "제목없음") this.scheduleBaseInfo.title else ""
        setStartDate(this.scheduleBaseInfo.startDate.convertToLocalDate())
        setEndDate(this.scheduleBaseInfo.endDate.convertToLocalDate())
        setSelectedColor(ResponseTodoColor.ResultTodoColor(this.scheduleBaseInfo.colorId, "", this.scheduleBaseInfo.colorCode))

        for(i in this.scheduleAreaInfo) {
            _locationWithDate[i.date.convertToYearMonthDate()] = i.value.mapToDocument().toMutableList()
        }

        setSelectedDate(this.scheduleBaseInfo.startDate.convertToYearMonthDate())
        this.scheduleAlarmInfo?.let { _todoAlarmList.value = it.toMutableList() }
    }

    private fun MutableList<TodoAreaData.AreaData>.mapToDocument() : List<ResponseKakaoLocation.Document> {
        return this.map { ResponseKakaoLocation.Document("", it.name, it.streetAddress ?: "", it.latitude ?: "", it.longitude ?: "", it.time ?: "") }
    }
}