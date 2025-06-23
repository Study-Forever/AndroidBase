package com.base.library.utils

/**
 * 常量池
 */
object DataStoreKey {
    const val TOKEN = "token"
    const val IDENTITY = "identity"
    const val PROFILE = "profile"
}

object Extra {
    const val PARAM_1 = "param_1"
    const val PARAM_2 = "param_2"
    const val PARAM_3 = "param_3"
    const val STUDENT_ID = "student_id"
    const val CLASS_CODE = "class_code"
}

object Role {
    const val STUDENT = "student"
    const val TEACHER = "teacher"
}

object QuestionSource {
    //自组题（题库）
    const val TYPE_LIBRARY = "assembly"

    //试卷
    const val TYPE_PAPER = "paper"

    //资源库
    const val TYPE_RESOURCE = "resource"

    //已发布的作业
    const val TYPE_PUBLISHED = "published"
}

object ResourceCategory {
    const val TEXT = "text"
    const val IMAGE = "image"
    const val AUDIO = "audio"
}

object ResultCode {
    const val SUCCESS = 1
    const val FAIL = 0
    const val NEED_LOGIN = 303
}

object QuestionCode {
    const val CODE_1 = "1" // 看图，无字，听音，跟读[始终不显示文字]
    const val CODE_2 = "2" // 看图，无字，听音，跟读[错误不显示文字]
    const val CODE_3 = "3" // 听声选图
    const val CODE_4 = "4" // 看图选声
    const val CODE_5 = "5" // 无图，无字，听音，跟读
    const val CODE_6 = "6" // 看图，有字，听音，跟读
    const val CODE_7 = "7" // 看图，无字，无音，自读
    const val CODE_8 = "8" // 听力
}