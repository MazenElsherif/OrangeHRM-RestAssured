Feature: OrangeHRM APIs

Background:
Given Genrate Token
 When Genrate Cookie
  
  Scenario: Delete
  And Get Candidtes
  Given Delete a recent record in the list
  
  Scenario: Add
   When Add a candidate
   
 Scenario: Search
   When Search with limit 50 and vacancy id 1 and sort order "DESC"
   
