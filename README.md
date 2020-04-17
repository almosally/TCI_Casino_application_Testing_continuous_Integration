# TCI Group Project
This is repository is for TCI Group Project.

[![pipeline status](https://git.fhict.nl/I377620/tci-group9/badges/master/pipeline.svg)](https://git.fhict.nl/I377620/tci-group9/-/commits/master)

#### Group members
* Kasper Hämäläinen
* Kheir Almosally
* Ali Alalao

## Getting Started
Clone this repo and spin it up with your favorite IDE, such as IntelliJ.

### Javadocs
The best place to start with this project is to read Javadocs.
It includes the expected behavior of the system and describes tests.

See `Installing`- chapter how to build Javadoc!

#### Diagrams
Additional information in forms of diagrams can be found from `./diagrams `- folder.

* C4
* High-level class Diagram

### Prerequisites
This is Gradle project and uses Gradle dependencies.
You should have Gradle installed in ur environment.

## Installing
How to build the project?

```gradle build```

### Javadoc
How to build Javadoc?

```gradle javadoc```

Javadoc then be found from `./build/docs/javadoc`

### Tests
How to run tests?

```gradle test```

You can also run `gradle check` to include `JaCoCo` and `Checkstyle` checks and reports.

### About Tests
Test classes are names based on their respective Interface classes, which were described in assignment.
Tests itself does not test behavior of interface, this would be bad practice.

#### Tests done

| 		  | Source Code   | Test                                                                                |
|---------|---------------|-------------------------------------------------------------------------------------|
| 		  | Casino        | addGame_GetGameByName_ReturnSameGame()                                              |
|         |               | addgameNameIsNullOrEmpty_ThrowIllegalArgumentException()                            |
|         |               | addMultipleGames_GetListOfGames_IsCopyOfCreatedGames()                              |
|         |               | addNewGame_GetListOfGames_ListIsNotEmpty()                                          |
|         |               | createCasino_CasinoContainsBetLoggingAuthorityAndBetTokenAuthority_True()           |
|         | GamingMachine | acceptWinner_AmountInPlayerCardIsUpdatedByCashier_MethodIsCalled()                  |
|         |               | acceptWinner_GetOpenBets_IsEmpty()                                                  |
|         |               | acceptWinner_WinningBetIsNotSameAsPlacedBet_ThrowIllegalArgument()                  |
|         |               | connectCard_GetPlayerCard_True()                                                    |
|         |               | newGamingMachine_GetOpenBets_Empty()                                                |
|         |               | newGamingMachine_GetPlayerCard_ThrowNoPlayerCardException()                         |
|         |               | newGamingMachine_IdIsCreatedByIDFactory_True()                                      |
|         |               | newGamingMachine_ReturnUniqueGamingMachineID_True()                                 |
|         |               | placeBet_BetAmountNotLargerThan0OrNull_ThrowIllegalArgumentException​               |
|         |               | placeBet_CashierDoesNotExceptBet_BetIsNotStored_Throw_BetNotExceptedException                                   |
|         |               | placeBet_CheckThatPlayerCardHasEnoughMoney_MethodIsCalled()                         |
|         |               | placeBet_GameDoesNotExceptBet_BetIsNotStored_Throw_NoCurrentRoundException()                                   |
|         |               | placeBet_GamingMachineAlreadyHaveABet_Prevent()                                     |
|         |               | placeBet_GamingMachineHasNoPlayerCard_ThrowNoPlayerCardException()                  |
|         |               | placeBet_GetOpenBets_NotEmpty()                                                     |
|         |               | placeBet_SubmitToGamingMachine_True()                                               |
|         | IDFactory     | idWithTypeX_IsDescribedByUUIDAndTimeStamp​()                                        |
|         |               | newGeneralID_IsDescribedByUUIDAndTimeStamp()                                        |
|         |               | newGeneralID_TimestampIsNow_True()                                                  |
|         |               | newGeneralIDType_CaseInsensitive_True​()                                            |
|         |               | newGeneralIDTypeNull_ReturnNull_True()                                              |
|         |               | newIdWithTypeX_IsInstanceOfGeneralID_True​()                                        |
|         |               | afterCreatingTwoIDs_TheyCanBeComparedByTimestamp_True()                             |
|---------|---------------|-------------------------------------------------------------------------------------|
|	      | Cashier       | createCashier_CashierContainsBetLoggingAuthority_True()                             |
|         |               | distributeGamblerCard_CheckIfHandOutGamblingCardCalled_PlayerCard()                 |
|         |               | distributeGamblerCard_CheckIfPlayerCardIsValid_True()                               |
|         |               | distributeGamblerCard_CheckIfPlayerCardDescribedByUniqueId_True()                   |
|         |               | distributeGamblerCard_CheckOutIfCardHasBeenDistributed_PlayerCard()                 |
|         |               | returnGamblerCard_IsHandOutGamblingCardCalled_True()                                |
|         |               | returnGamblerCard_IsCardHaasBeenReturned_True()                                     |
|         |               | returnGamblerCard_MoneyInCardZero_True()                                            |
|         |               | returnGamblerCard_BetsIdInCardZero_True()                                           |
|         |               | checkIfBetIsValid_MoneyInPlayCardEqualOrMoreThanMoneyForBet_True()                  |
|         |               | addAmount_AddMoneyToThePlayerCard_True()                                            |
|         |               | addAmount_AddValidMoneyToThePlayerCardUsingParameter_True(long amount)              |
|         |               | addAmount_AddNegativeAmountOfMoneyToThePlayerCard_ThrowIllegalArgumentException()   |
|         |               | addAmount_AddMoneyToAnInvalidPlayerCard_ThrowIllegalArgumentException()             |
|         | PlayerCard    | returnBetIDs_GetListOfBetId_ListOfIds()                                             |
|         |               | returnBetIDsAndClearCard_GetListOfBetIdAndCleanCard_ListOfIds()                     |
|         |               | generateNewBetID_GenerateNewBetIdAndAddTheIdToTheBetIDsList_BetID()                 |
|         |               | getNumberOfBetIDs_ReturnNumberOfBitIds_IntegerNumber()                              |
|         |               | getCardID_ReturnPlayCardId_CardId()                                                 |
|         |               | generateNewBetID_CheckIdBetIDUnique_BetID()                                         |
|         |               | getNumberOfBetIDs_BetIdsIsNotNull_ListOfIds()                                       |
|         |               | getNumberOfBetIDs_BetIdsContains7IDs_ListOfIds()                                    |
|---------|---------------|-------------------------------------------------------------------------------------|                                                                                     |
|         | BettingRound  | getBettingRoundID_checkBettingRoundID_BettingRoundID()                              |
|         |               | placeBet_PlacingBetAndAddedToTheListOfBets_True()                                   |
|         |               | getAllBetsMade_returnAllBetsMadeDuringThisRound()                                   |
|         |               | getBetToken_checkingGetToken_returnToken()                                          |
|         |               | numberOFBetsMade_trackingNumberOfBets_returnNumber()                                |
|         |               | trackingNumberOfBets_NoBetPlaced_returnEmptyList()                                  |
|         | Gaming        | startBettingRound_StartBetRound_successfulStart()                                   |
|         |               | acceptBet_BetSuccessfullyAccepted_BetRoundAndBettingAreValid()                      |
|         |               | acceptBet_CheckBet_betIsNotValid_ThrowException()                                   |
|         |               | isBettingRoundFinished_endOFBettingRound_returnTrue()                               |
|         |               | endOFBettingRound_returnFalse()                                                     |
|         | GameRule      | createGameRule_ContainsToBetTokenAuthority_True()                                   |
|         |               | determineWinner_returnTheWinner()           ​                                       |
|         |               | determineWinner_CheckWinnerInBetRound_WhereSuccessfulBetPlaced()                    |
|         |               | heckWinnerInBetRound_ThrowsException_WhereNoBetsPlaced()                            |
|         |               | getMaxBetsPerRound_checkTheMaxBetPlaced_returnNumber()                              |  
|         |               | checkTheMaxBetPlaced_ThrowException()                                               |                                                                                          |
|---------|---------------|-------------------------------------------------------------------------------------|

### JaCoCo
The project uses JaCoCo to test code coverage.

JaCoCo report can be found under `./build/jacoco-reports/test/html/index.html`

Following classes are excluded from the code coverage, as instructed in the assignment:

* `bettingauthorityAPI`

* `casino/bet`

Final code coverage is: `85%`

![code_coverage](https://i.ibb.co/w61sVr7/Screenshot-2.png)

### Checkstyle
The project uses Checkstyle to verify the coding style.

Config file uses is from [WSO2 Inc](https://github.com/wso2/code-quality-tools/blob/master/checkstyle/checkstyle.xml).

Project group made decision to allow package imports with wildcard. These are currently listed as `WARN` when checkstyle 
is tested, but it passes.

Checkstyle report for Main classes can be found under `./build/reports/checkstyle/main.html`

Checkstyle report can Test classes can be found under `./build/reports/checkstyle/test.html`

## CI Pipeline
The project uses GitLab CI Pipeline. Pipeline creates Docker env where `gradle check` is run.
Only if the pipeline passes, merging to master is approved. Corresponding notifications are sent to the merger.

The project uses `protected` `Master`- branch so that pushing to master will not include any untested code.

