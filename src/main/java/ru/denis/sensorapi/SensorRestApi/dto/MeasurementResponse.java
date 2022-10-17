package ru.denis.sensorapi.SensorRestApi.dto;

import java.util.List;

public class MeasurementResponse {
    private List<MeasurementDTO> measurementDTOList;

    public MeasurementResponse(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }

    public List<MeasurementDTO> getMeasurementDTOList() {
        return measurementDTOList;
    }

    public void setMeasurementDTOList(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }
}
