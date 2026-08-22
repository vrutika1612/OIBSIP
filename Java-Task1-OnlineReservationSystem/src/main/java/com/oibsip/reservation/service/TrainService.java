package com.oibsip.reservation.service;

import com.oibsip.reservation.dao.TrainDAO;
import com.oibsip.reservation.exception.TrainNotFoundException;
import com.oibsip.reservation.model.Train;

public class TrainService {
    private final TrainDAO trainDAO;
    public TrainService(TrainDAO trainDAO) { this.trainDAO = trainDAO; }
    public Train findRequired(int trainNumber) { return trainDAO.findByTrainNumber(trainNumber).orElseThrow(() -> new TrainNotFoundException(trainNumber)); }
}
