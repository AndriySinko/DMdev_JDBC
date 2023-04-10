package dto;

public record ModelsFilter (int limit,
                            int offset,
                            String modelName,
                            int companyId) {
}
