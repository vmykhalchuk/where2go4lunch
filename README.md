# BUILDING
Project requires:
* Java 1.7+
* Gradle

Build project:
* gradle build

Start server:
* ./start.sh

Populate example users, restaurants and lunch menues:
* ./example.sh

# GENERAL RULES:
## Every REST endpoint might return more descriptive exception:
If HTTP Code 4XX or 5XX is returned - response body also contains the exception in JSON format:
{"errorMessage":"XXXXXXX","errorClass":"java.fully.specified.Class"}

## Initialize Server DB with admin/admin entry
SERVER INIT - Since DB used is an Inmemory DB, Hack is present in order to get it Initialized (also init.sql didn't work, so had to do this way):
curl -v -X GET "${SERVER_URL}/login-init" 

## Login command
curl -v -X POST -H "Content-Type: application/x-www-form-urlencoded" -c cookies.txt -d 'username=admin&password=admin' "${SERVER_URL}/login"

## Change password for current user (every user can change by itself)
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"oldPassword":"admin","newPassword":"pass2"}' "${SERVER_URL}/users/admin/password"


# ADMIN COMMANDS (MANAGE SIMPLE USERS):

## Add new user
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"username":"nhuha","password":"password","firstname":"Nataliya","lastname":"Huha"}' "${SERVER_URL}/users/"
## Modify user firstname or lastname or both
curl -v -X PUT -H "Content-Type: application/json" -b cookies.txt -d '{"lastname":"Huha2"}' "${SERVER_URL}/users/nhuha"
## Delete user
curl -v -X DELETE -b cookies.txt "${SERVER_URL}/users/nhuha"
## Get user info
curl -v -X GET -b cookies.txt "${SERVER_URL}/users/nhuha"


# ADMIN COMMANDS (MANAGE RESTAURANTS AND MENU)

## Add new restaurant
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"name": "Hichkalnja"}' "${SERVER_URL}/restaurant"
## Add new Lunch Menu to restaurant 
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"price": "56 uah", "dishes": ["huagra","test"]}' "${SERVER_URL}/restaurant/1/menu"

## Delete restaurant
curl -v -X DELETE -b cookies.txt "${SERVER_URL}/restaurant/1"
## Delete restaurant's Lunch Menu
curl -v -X DELETE -b cookies.txt "${SERVER_URL}/restaurant/1/menu/2"

## Get restaurant with list of Lunch Menu items
curl -v -X GET -b cookies.txt "${SERVER_URL}/restaurant/1"

## Get restaurants list
curl -v -X GET -b cookies.txt "${SERVER_URL}/restaurant"

# USER COMMANDS

## Get restaurants list
curl -v -X GET -b cookies.txt "${SERVER_URL}/restaurant"
## Get restaurant with list of Lunch Menu items
curl -v -X GET -b cookies.txt "${SERVER_URL}/restaurant/1"

## Give a Vote to LunchMenu
curl -v -X GET -b cookies.txt "${SERVER_URL}/restaurant/1/menu/2/giveVote"

# GET VOTING RESULTS

## Get voting results for today (returns top voted lunch menue's and their locations, can be more then one if same number of votes)
curl -v -X GET -b cookies.txt "${SERVER_URL}/where2go"
## Get only one result (if more then one available - then randomly select one!)
curl -v -X GET -b cookies.txt "${SERVER_URL}/where2go/one"
