package bettingauthorityapi;

/**
 * Compound BettingAuthority Class, containing both a IBetLoggingAuthority and IBetTokenAuthority
 * Any casino MUST use one object instantiation of this class.
 *
 * <div>
 *     <strong>Expected behaviour:</strong>
 *     <ul>
 *         <li>On casino instantiation, BettingAuthority must be used.</li>
 *     </ul>
 * </div>
 *
 */
public final class BettingAuthority {
    private static final IBetLoggingAuthority logging = new BetLoggingAuthority();;
    private static final IBetTokenAuthority token  = new BetTokenAuthority();;

    public BettingAuthority() {
    }

    public IBetLoggingAuthority getLoggingAuthority() {
        return logging;
    }

    public IBetTokenAuthority getTokenAuthority() {
        return token;
    }
}
