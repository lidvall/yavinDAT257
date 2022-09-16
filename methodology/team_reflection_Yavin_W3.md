# Team reflection - Yavin - Week 3

## Customer Value and Scope
- The product is an app/website that gives a view over the participation amongst first time voters in the municipal elections in Sweden.
The results will be filterable in various ways, and the user will be able to use categorizations of the municipalities in order to gain deeper insights,
such as only looking at mainly rural municipalities or mainly urban municipalities.
  - The above project scope was the initial version, however it has been adapted during the week to include more data due to the fact that a better database was found.
The new database includes election data on all three election-levels in Sweden, ranging from municipiality to the national level. This will further strengthen our ability
to provide customer value. The application will now be a one-stop shop for all your election data needs.
  - Additional extensions that we are looking into is the ability to view results for each party in the elections, and possibly even the mandate ditribution.

- Since extending the scope of the project our success criterias as also been modified. After completing the project pitch we relaized that we needed to further expand our idea, we therefore decided on changing database for our open data, and also increasing the total amount of data that will be visualized in our application.
  - This affected our success critera, we now have a greater amount of functions that need to be implemented in order for us to reach the MVP-level previously set at just displaying data from one election and year.
  - Furthermore our success critera also consists of factors like how well the team communication and project methodology have worked, this due to the fact that if they are held at a sufficent level, the likely hood of sucessfully completeing the project increases.
  - Our team communication has been working really well through out the week, where all meetings and other asyncronized communcation has occured online.
  - In terms of the project methodology, our use of the scrumboard has fallen a little bit short on some people in the team, this was brought up during our friday-meeting by Johan and we all agreed that we should try to be better at keeping it updated. (The problem was not that the work hadn't been done, the problem was the status update)

- During this week we have not used any KPIs, however moving forward we will be looking specfically on the ratio of Tasks / Tasks to be done, which should equal 1 at the end of each week. Two more KPIs are set to be formulated during the next team-meeting, prior to the next supervison.
- We selected userstories with the aim for them to be mutually exclusive and collectivley exhaustive, we then broke these stories down into tasks incrementally using a top down logic in the effort to get down to fundamental steps for tasks.

## Design decisions and product structure

- Initially we decided to use Sveriges Kommuner och Regioner API. This posed issues since the data was not complete, which was realized after retrieval. Thus we decided to change API and did so over the following criteria, the API has varied data to allow for easy expansion of use cases. We are currently writing the program with attempted low coupling high cohesion and will soon begin working on the interface. In that stage we are looking to utilize a MVC strucuture.

- We have a varying degree of documentation in the code at this stage, it started of stronger in the beginning of the week but in it’s current state large parts of the code is undocumented. This is a priority when the code is completely functional and a necessity to other functionalities in order to efficiently be able to work individually with each others contributions.

- We mostly rely on the scrumboard to define and coordinate tasks between meetings but also keep the whole team continuously updated on developments in individuals tasks via chat.

## Application of Scrum

- As previously mentioned, scrum board, but also the structure of daily scrums have been utilized.
- Both with varying degrees of success. As the scrum board was not as explicit as it could have been, a task was misinterpreted. Luckily the task was not made in vain, however a bit premature. Next week's tasks now build upon the work of that task. However, as such, the task of expanding the data covered in the application now also entails the task of deciding which data to include. Furhtermore, there needs to be a greater attention to keeping it up to date to ensure usefulness and complete information for all of the team.
- The daily scrum worked better, however we realize it is difficult to retain the structure of the meetings as there is a natural overlap between issues and resolving them between the different team members assigned tasks. This is likely unavoidable as it is natural to bring in the work being done in another task if it alleviates the issues prevalent in another task. We just need to be aware of this fact for upcoming scrum meetings to ensure we are able to get back to the over all structure of letting each member present their progress in the scrum meeting.
