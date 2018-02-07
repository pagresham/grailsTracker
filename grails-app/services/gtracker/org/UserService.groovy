package gtracker.org

import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    def getTheAuthor() {
        return "Pierce Effing Gresham"
    }
}
