package telapo.meteorwatcher.dal.model

import java.time.LocalDateTime

class Comment(var dt: LocalDateTime, var text: String) {}

class Observation(var Comments: List<Comment>, var Periods: List<Period>) {

}

class Period {

}

class SwarmResults {

}