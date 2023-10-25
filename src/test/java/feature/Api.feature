Feature: OrangeHRM APIs


  Scenario: Delete,Add,Search
  Given Genrate Token
  When Genrate Cookie
  And Get Candidtes
  Given Delete a recent record in the list
   When Add a candidate
   When Search with limit 50 and vacancy id 1 and sort order "DESC"
   
