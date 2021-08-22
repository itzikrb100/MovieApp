/*
 *             COPYRIGHT 2021 MOTOROLA SOLUTIONS INC. ALL RIGHTS RESERVED.
 *                   MOTOROLA SOLUTIONS CONFIDENTIAL RESTRICTED
 *                            TEMPLATE VERSION R01.03
 */
package com.example.bl.tasks

import com.example.bl.repository.db.entities.MovieEntity

interface ITaskRunner {
    fun run(list: MutableList<MovieEntity>?, block: suspend(list: MutableList<MovieEntity>?) -> Unit)
    fun cancel()
}