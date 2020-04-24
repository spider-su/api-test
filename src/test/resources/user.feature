Feature: the user can be retrieved
  Scenario: client makes call to GET /users
    Given user with id 1
    When the client calls /users/{id} 1
    Then the client receives status code of 200
    And the response like JSON users

  Scenario: client makes call to GET /users
    Given user with id 2
    When the client calls /users/{id} 2
    Then the client receives status code of 200
    And the client receives name Bob