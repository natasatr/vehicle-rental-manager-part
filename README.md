The fourth part of the application refers to JSP technology. It demonstrates that only an employee with the Manager role can log into the system(Authorization and Authentication -> implemented. :)),
while others (ADMIN, OPERATOR, CLIENT) can't. Therefore, the system doesn't include registration. The Manager logs into the system using a username
and password, meaning that the employee must already be registered in the system in order to access this part with their credentials. Registration
is handled in the Spring part of the application.
In this part of the application, the Manager can view and create promotions and posts, as well as search them by title or content respectively.

