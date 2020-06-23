package com.wander.covid.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
public class CovidData implements Serializable
{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private int id;
  private Date date;
  private int active;
  private int confirmed;
  private int deceased;
  private int recovered;
  private int tested;

  public int getActive()
  {
    return active;
  }

  public void setActive(int active)
  {
    this.active = active;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public int getConfirmed()
  {
    return confirmed;
  }

  public void setConfirmed(int confirmed)
  {
    this.confirmed = confirmed;
  }

  public int getDeceased()
  {
    return deceased;
  }

  public void setDeceased(int deceased)
  {
    this.deceased = deceased;
  }

  public int getRecovered()
  {
    return recovered;
  }

  public void setRecovered(int recovered)
  {
    this.recovered = recovered;
  }

  public int getTested()
  {
    return tested;
  }

  public void setTested(int tested)
  {
    this.tested = tested;
  }
}
