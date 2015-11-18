# SETUP

SERVER_URL="http://localhost:8080"

curl -v -X GET "${SERVER_URL}/login-init"
curl -v -X POST -H "Content-Type: application/x-www-form-urlencoded" -c cookies.txt -d 'username=admin&password=admin' "${SERVER_URL}/login"

# Hichkalnja menu
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"name": "Hichkalnja"}' "${SERVER_URL}/restaurant"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"price": "56 uah", "dishes": ["huagra","test"]}' "${SERVER_URL}/restaurant/1/menu"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"price": "66 uah", "dishes": ["huagra","test","zebra"]}' "${SERVER_URL}/restaurant/1/menu"
# Rugaljna menu
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"name": "Rugaljna"}' "${SERVER_URL}/restaurant"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"price": "50 uah", "dishes": ["sup","kompot"]}' "${SERVER_URL}/restaurant/2/menu"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"price": "66 uah", "dishes": ["water","bulka","zebra"]}' "${SERVER_URL}/restaurant/2/menu"

# Add user nhuha
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"username":"nhuha","password":"password","firstname":"Nataliya","lastname":"Huha"}' "${SERVER_URL}/users/"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"username":"user2","password":"password","firstname":"Nataliya","lastname":"User2"}' "${SERVER_URL}/users/"
curl -v -X POST -H "Content-Type: application/json" -b cookies.txt -d '{"username":"user3","password":"password","firstname":"Nataliya","lastname":"User3"}' "${SERVER_URL}/users/"