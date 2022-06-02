import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class ExampleBot {
    public static void main(final String[] args) {
        //Se crea una variable String para almacenar el token
        final String token = Token.getToken();

        //Se crea el cliente utilizado el token
        final DiscordClient client = DiscordClient.create(token);

        //Se crea una pasarela usando el cliente
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            //Se crea una variable Message para recivir el mensage recivido
            final Message message = event.getMessage();

            //Comprobacion de si el mensaje recivido equivale a !ping
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();

                //Se crea el mensaje de respuesta Pong!
                channel.createMessage("Pong!").block();
            }
        });

        //Se cierra la pasarela
        gateway.onDisconnect().block();
    }
}
