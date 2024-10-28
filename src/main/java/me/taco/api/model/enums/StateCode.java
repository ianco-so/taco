package me.taco.api.model.enums;

public enum StateCode {
    AC("Acre",                  "Rio Branco"),
    AL("Alagoas",               "Maceió"),
    AP("Amapá",                 "Macapá"),
    AM("Amazonas",              "Manaus"),
    BA("Bahia",                 "Salvador"),
    CE("Ceará",                 "Fortaleza"),
    DF("Distrito Federal",      "Brasília"),
    ES("Espírito Santo",        "Vitória"),
    GO("Goiás",                 "Goiânia"),
    MA("Maranhão",              "São Luís"),
    MT("Mato Grosso",           "Cuiabá"),
    MS("Mato Grosso do Sul",    "Campo Grande"),
    MG("Minas Gerais",          "Belo Horizonte"),
    PA("Pará",                  "Belém"),
    PB("Paraíba",               "João Pessoa"),
    PR("Paraná",                "Curitiba"),
    PE("Pernambuco",            "Recife"),
    PI("Piauí",                 "Teresina"),
    RJ("Rio de Janeiro",        "Rio de Janeiro"),
    RN("Rio Grande do Norte",   "Natal"),
    RS("Rio Grande do Sul",     "Porto Alegre"),
    RO("Rondônia",              "Porto Velho"),
    RR("Roraima",               "Boa Vista"),
    SC("Santa Catarina",        "Florianópolis"),
    SP("São Paulo",             "São Paulo"),
    SE("Sergipe",               "Aracaju"),
    TO("Tocantins",             "Palmas");

    private final String fullname;
    private final String capital;

    StateCode(String fullname, String capital) {
        this.fullname = fullname;
        this.capital = capital;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getCapital() {
        return this.capital;
    }
}
