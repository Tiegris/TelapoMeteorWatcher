package telapo.meteorwatcher.dal.model.observation

data class SwarmResults(
    val Name: String,
    var count_m5: Int = 0,
    var count_m4: Int = 0,
    var count_m3: Int = 0,
    var count_m2: Int = 0,
    var count_m1: Int = 0,
    var count_0: Int = 0,
    var count_1: Int = 0,
    var count_2: Int = 0,
    var count_3: Int = 0,
    var count_4: Int = 0,
    var count_5: Int = 0,
) {
    val Count : Int get() {
        return count_0+count_1+count_2+count_3+count_4+count_5+count_m1+count_m2+count_m3+count_m4+count_m5
    }
    fun Add(mg: Int) {
        if (mg == 5) count_5++
        if (mg == 4) count_4++
        if (mg == 3) count_3++
        if (mg == 2) count_2++
        if (mg == 1) count_1++
        if (mg == 0) count_0++
        if (mg == -1) count_m1++
        if (mg == -2) count_m2++
        if (mg == -3) count_m3++
        if (mg == -4) count_m4++
        if (mg == -5) count_m5++
    }
}