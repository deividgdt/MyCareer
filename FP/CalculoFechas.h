/******************************************************************************
* Fichero interfaz del modulo CalculoFechas.
* Suma un numero de días X a una fecha Y y lo devuelve por las variables
* David L. Version 1.0 - 04/01/2021
******************************************************************************/
#pragma once
/**************************************************************************
Variables y tipos gloables que se van ser usadas externamente.
**************************************************************************/
extern int day, month, year;
extern int newDay, newMonth, newYear;

/**************************************************************************
Funcion que suma días a una fecha concreta.
**************************************************************************/
void sumDays(int d, int m, int y, int daysToSum);
