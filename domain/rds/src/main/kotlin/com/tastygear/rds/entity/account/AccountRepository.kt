package com.tastygear.rds.entity.account

import org.springframework.data.repository.CrudRepository

interface AccountRepository<T : Account> : CrudRepository<T, String>