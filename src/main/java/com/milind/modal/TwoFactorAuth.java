package com.milind.modal;

import com.milind.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth{
    private boolean isEnabled =false;
    private VerificationType sendTo;

}