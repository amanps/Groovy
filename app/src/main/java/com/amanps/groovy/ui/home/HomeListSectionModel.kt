package com.amanps.groovy.ui.home

import com.amanps.groovy.data.model.Program

data class HomeListSectionModel(val type: Int,
                                val sectionNameResId: Int,
                                val programs: List<Program>)