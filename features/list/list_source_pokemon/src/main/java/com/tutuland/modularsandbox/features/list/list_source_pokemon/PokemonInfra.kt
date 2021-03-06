package com.tutuland.modularsandbox.features.list.list_source_pokemon

import com.tutuland.modularsandbox.features.list.CardList
import com.tutuland.modularsandbox.libraries.data.cards.Card
import com.tutuland.modularsandbox.libraries.tracking.Tracker
import com.tutuland.modularsandbox.libraries.utils.isNotNullOrEmpty
import io.pokemontcg.Pokemon
import io.pokemontcg.model.SuperType
import io.reactivex.Scheduler
import io.reactivex.Single

class PokemonGateway(
    private val pokeDex: Pokemon,
    private val storage: Card.Storage,
    private val tracker: Tracker,
    private val scheduler: Scheduler
) : CardList.Source {
    override fun getCards(forceRefresh: Boolean): Single<List<Card.Data>> =
        if (!forceRefresh && storage.listCache.isNotNullOrEmpty()) Single.just(storage.listCache)
        else pokeDex.card()
            .observeAll()
            .subscribeOn(scheduler)
            .map { pokemons ->
                pokemons.filter { card -> card.supertype == SuperType.POKEMON }.map { pokemon ->
                    val description = buildString {
                        append("Name: ${pokemon.name}")
                        pokemon.types?.let { typeList ->
                            typeList.forEachIndexed { i, type ->
                                if (i == 0) append("\nType: ${type.displayName}")
                                else append(" | ${type.displayName}")
                            }
                        }
                        pokemon.evolvesFrom?.let { previous ->
                            append("\nEvolves from: $previous")
                        }
                    }

                    Card.Data(
                        title = "${pokemon.name} (${pokemon.number})",
                        imageUrl = pokemon.imageUrlHiRes,
                        description = description
                    )
                }.also { storage.listCache = it }
            }.firstOrError()

    override fun select(card: Card.Data) {
        storage.selectedCard = card
        tracker track Tracker.Event.UserInteraction("card", "selected", card.title)
    }
}