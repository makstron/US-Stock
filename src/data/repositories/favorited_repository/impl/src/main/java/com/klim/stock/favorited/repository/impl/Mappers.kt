package com.klim.stock.favorited.repository.impl

import com.klim.stock.favorited.repository.impl.datasource.dto.FavoritedDTO
import com.klim.stock.favorited.usecase.api.entity.FavoritedEntity


fun FavoritedDTO.map(): FavoritedEntity = FavoritedEntity(
    this.name
)