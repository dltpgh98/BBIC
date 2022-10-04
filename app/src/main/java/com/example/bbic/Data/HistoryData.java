package com.example.bbic.Data;

public class HistoryData {
    private String start_pos, end_pos;

    public HistoryData(String start_pos, String end_pos) {
        this.start_pos = start_pos;
        this.end_pos = end_pos;
    }

    public String getStart_pos() {
        return start_pos;
    }

    public String getEnd_pos() {
        return end_pos;
    }
}
