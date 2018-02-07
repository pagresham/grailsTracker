package gtracker.org

class TestingController {

    def index() {
        render "Oh hi, its me world"

    }

    def home() {
        render view: "home", model: [author: "Pierce Gresham"]

    }
}
