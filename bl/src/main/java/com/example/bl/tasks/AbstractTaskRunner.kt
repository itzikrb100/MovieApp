/*
 *             COPYRIGHT 2021 MOTOROLA SOLUTIONS INC. ALL RIGHTS RESERVED.
 *                   MOTOROLA SOLUTIONS CONFIDENTIAL RESTRICTED
 *                            TEMPLATE VERSION R01.03
 */

package com.example.bl.tasks

import com.example.bl.repository.db.entities.MovieEntity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class AbstractTaskRunner(dispatcher: CoroutineDispatcher): CoroutineScope, ITaskRunner {
    private val job = Job()
    override val coroutineContext: CoroutineContext = dispatcher + job

    override fun run(list:  MutableList<MovieEntity>?, block: suspend(list: MutableList<MovieEntity>?) -> Unit) {
         launch {
             block(list)
        }
    }

    override fun cancel() {
       if(job.isActive) job.cancel()
    }
}