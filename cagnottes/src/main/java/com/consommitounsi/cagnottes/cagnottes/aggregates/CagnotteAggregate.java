package com.consommitounsi.cagnottes.cagnottes.aggregates;

import com.consommitounsi.cagnottes.cagnottes.commands.AjouterDonCommand;
import com.consommitounsi.cagnottes.cagnottes.commands.CreerCagnotteCommand;
import com.consommitounsi.cagnottes.cagnottes.events.CagnotteCreeeEvent;
import com.consommitounsi.cagnottes.cagnottes.events.DonAjouteEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CagnotteAggregate {
    @AggregateIdentifier
    private String cagnotteId;
    private String nom;
    private String description;
    private double montantCible;
    private double montantCollecte;

    public CagnotteAggregate() {
        // Constructeur par défaut requis par Axon
    }

    @CommandHandler
    public CagnotteAggregate(CreerCagnotteCommand command) {
        apply(new CagnotteCreeeEvent(command.getCagnotteId(), command.getNom(), command.getDescription(), command.getMontantCible()));
    }

    @EventSourcingHandler
    public void on(CagnotteCreeeEvent event) {
        this.cagnotteId = event.getCagnotteId();
        this.nom = event.getNom();
        this.description = event.getDescription();
        this.montantCible = event.getMontantCible();
        this.montantCollecte = 0.0;
    }

    @CommandHandler
    public void handle(AjouterDonCommand command) {
        apply(new DonAjouteEvent(command.getCagnotteId(), command.getMontant()));
    }

    @EventSourcingHandler
    public void on(DonAjouteEvent event) {
        this.montantCollecte += event.getMontant();
    }
}

