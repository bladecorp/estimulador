package com.sysdt.estimuladorapp.model;

public class Estimulador {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column estimulador.id
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column estimulador.serie
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    private String serie;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column estimulador.idPaciente
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    private Integer idPaciente;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column estimulador.id
     *
     * @return the value of estimulador.id
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column estimulador.id
     *
     * @param id the value for estimulador.id
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column estimulador.serie
     *
     * @return the value of estimulador.serie
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public String getSerie() {
        return serie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column estimulador.serie
     *
     * @param serie the value for estimulador.serie
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column estimulador.idPaciente
     *
     * @return the value of estimulador.idPaciente
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public Integer getIdPaciente() {
        return idPaciente;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column estimulador.idPaciente
     *
     * @param idPaciente the value for estimulador.idPaciente
     *
     * @mbggenerated Sat Aug 27 13:56:24 CDT 2016
     */
    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }
}