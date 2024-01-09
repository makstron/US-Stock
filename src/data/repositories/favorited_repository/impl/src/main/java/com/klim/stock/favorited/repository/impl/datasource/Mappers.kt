package com.klim.stock.favorited.repository.impl.datasource

import com.klim.stock.database.entity.FavoritedEntity
import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO


fun FavoritedEntity.map(): FavoritedDTO = FavoritedDTO(
    name = this.symbol
)