package io.digitalreactor.vendor.yandex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by flaidzeres on 12.06.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("total_rows")
    private Long totalRows;

    private Boolean sampled;

    @JsonProperty("sample_share")
    private Double sampleShare;

    @JsonProperty("sample_size")
    private Long sampleSize;

    @JsonProperty("sample_space")
    private Long sampleSpace;

    @JsonProperty("data_lag")
    private Integer dataLag;

    @JsonProperty("query")
    private Request request;

    private List<List<Double>> totals;

    private List<Double> min;

    private List<Double> max;

    @JsonProperty("data")
    private List<Data> datas;

    public Long getTotalRows() {
        return totalRows;
    }

    public Boolean getSampled() {
        return sampled;
    }

    public Double getSampleShare() {
        return sampleShare;
    }

    public Long getSampleSize() {
        return sampleSize;
    }

    public Long getSampleSpace() {
        return sampleSpace;
    }

    public Integer getDataLag() {
        return dataLag;
    }

    public Request getRequestTable() {
        return request;
    }

    public List<List<Double>> getTotals() {
        return totals;
    }

    public List<Double> getMin() {
        return min;
    }

    public List<Double> getMax() {
        return max;
    }

    public List<Data> getDatas() {
        return datas;
    }



    public static class Data {
        private List<Map<String, String>> dimensions;
        private List<List<Double>> metrics;

        public List<Map<String, String>> getDimensions() {
            return dimensions;
        }

        public List<List<Double>> getMetrics() {
            return metrics;
        }
    }

}
