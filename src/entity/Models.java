package entity;

public class Models {
    private Integer id;
    private Integer companyId;
    private String modelName;
    private Integer modelYears;
    private Integer engineId;
    private Integer transmissionId;

    public Models() {
    }

    public Models(Integer id, Integer companyId, String modelName, Integer modelYears, Integer engineId, Integer transmissionId) {
        this.id = id;
        this.companyId = companyId;
        this.modelName = modelName;
        this.modelYears = modelYears;
        this.engineId = engineId;
        this.transmissionId = transmissionId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelYears(Integer modelYears) {
        this.modelYears = modelYears;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getModelYears() {
        return modelYears;
    }

    public Integer getEngineId() {
        return engineId;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    @Override
    public String
    toString() {
        return "Models{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", modelName='" + modelName + '\'' +
                ", modelYears=" + modelYears +
                ", engineId=" + engineId +
                ", transmissionId=" + transmissionId +
                '}';
    }
}
