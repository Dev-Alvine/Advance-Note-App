Architecture
This project uses MVVM architecture 
 Reason:

more clear and intentional separation of concerns
single source of truth for our UI state 
simpler and more direct UI testability, since we can define how the UI should look like with our state objects
Packaging Structure
data
handles getting and mutating data from needed sources
domain
handles encasing business logic for reuse
presentation
handles displaying data on device
