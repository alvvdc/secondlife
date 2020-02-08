package com.iesvirgendelcarmen.secondlife.model

enum class Category {
    MOTOR, INMOBILIARIA, EMPLEO, SERVICIOS, OTROS;

    companion object {
        fun parse(category :String) :Category {

            when (category.trim().toLowerCase()) {
                "motor" -> return MOTOR
                "inmobiliaria" -> return INMOBILIARIA
                "empleo" -> return EMPLEO
                "servicios" -> return SERVICIOS
            }
            return OTROS
        }
    }
}