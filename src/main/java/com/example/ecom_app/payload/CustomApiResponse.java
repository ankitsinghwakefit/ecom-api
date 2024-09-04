package com.example.ecom_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomApiResponse {
    public String message;
    private boolean httpStatus;
}
