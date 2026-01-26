package com.venda.venda.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter  // ✅ Gera apenas name()
public enum Perfil {
    ADMIN,
    CLIENTE;
    
    // ✅ SIMPLES - usa o name() padrão do Enum
}
