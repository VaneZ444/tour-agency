package xyz.vanez.orchestrator.state;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class BookingStateMachine {

    private StateMachine<BookingState, BookingEvent> stateMachine;

    public BookingStateMachine() throws Exception {
        Builder<BookingState, BookingEvent> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(BookingState.STARTED)
                .states(EnumSet.allOf(BookingState.class));

        builder.configureTransitions()
                .withExternal()
                .source(BookingState.STARTED)
                .target(BookingState.CLIENT_VERIFICATION_PENDING)
                .event(BookingEvent.START_PROCESS)
                .and()
                .withExternal()
                .source(BookingState.CLIENT_VERIFICATION_PENDING)
                .target(BookingState.BOOKING_CREATION_PENDING)
                .event(BookingEvent.CLIENT_VERIFIED)
                .and()
                .withExternal()
                .source(BookingState.CLIENT_VERIFICATION_PENDING)
                .target(BookingState.FAILED)
                .event(BookingEvent.CLIENT_VERIFICATION_FAILED)
                .and()
                .withExternal()
                .source(BookingState.BOOKING_CREATION_PENDING)
                .target(BookingState.PAYMENT_PROCESSING_PENDING)
                .event(BookingEvent.BOOKING_CREATED)
                .and()
                .withExternal()
                .source(BookingState.BOOKING_CREATION_PENDING)
                .target(BookingState.FAILED)
                .event(BookingEvent.BOOKING_CREATION_FAILED)
                .and()
                .withExternal()
                .source(BookingState.PAYMENT_PROCESSING_PENDING)
                .target(BookingState.COMPLETED)
                .event(BookingEvent.PAYMENT_PROCESSED)
                .and()
                .withExternal()
                .source(BookingState.PAYMENT_PROCESSING_PENDING)
                .target(BookingState.FAILED)
                .event(BookingEvent.PAYMENT_PROCESSING_FAILED);

        stateMachine = builder.build();
        stateMachine.start();
    }

    public void startProcess(String bookingId) {
        stateMachine.getExtendedState().getVariables().put("bookingId", bookingId);
        stateMachine.sendEvent(BookingEvent.START_PROCESS);
    }

    public void sendEvent(BookingEvent event) {
        stateMachine.sendEvent(event);
    }

    public BookingState getState() {
        return stateMachine.getState().getId();
    }
}
