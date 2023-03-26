package V3;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.Scanner;

public class server {
    public static void main(String[] args) throws IOException {
        String[] jokeFiles = new String[]{
                "jokes/joke1.txt",
                "jokes/joke2.txt",
                "jokes/joke3.txt",
                "jokes/joke4.txt",
                "jokes/joke5.txt",
                "jokes/joke6.txt",
                "jokes/joke7.txt",
                "jokes/joke8.txt",
                "jokes/joke9.txt",
                "jokes/joke10.txt"
        };

        String Error_File = "jokes/Error_Page.txt";

        try (DatagramSocket socket = new DatagramSocket(6698)) {
            boolean x = true;

            while (x) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String clientInfo = "Client IP: " + receivePacket.getAddress() + "\n" +
                        "Client port: " + receivePacket.getPort() + "\n";
                System.out.println(clientInfo);

                System.out.println("A client is trying to connect. Do you want to approve (y) or deny (n) the connection?");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                boolean approved = input.equalsIgnoreCase("y");

                String str = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client's response is : " + str);

                byte[] sendBuffer;

                if (approved) {
                    switch (str) {
                        case "get_joke":
                            Random random = new Random();
                            int jokeIndex = random.nextInt(10);
                            String joke = Files.readString(Paths.get(jokeFiles[jokeIndex]));
                            sendBuffer = joke.getBytes();
                            break;
                        case "get_fact":
                            String fact = getRandomScienceFact();
                            sendBuffer = fact.getBytes();
                            break;
                        case "get_history":
                            String history = getRandomHistoricalEvent();
                            sendBuffer = history.getBytes();
                            break;
                        default:
                            String errorMessage = Files.readString(Paths.get(Error_File));
                            sendBuffer = errorMessage.getBytes();
                            break;
                    }
                } else {
                    String errorMessage = Files.readString(Paths.get(Error_File));
                    sendBuffer = errorMessage.getBytes();
                }

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

                socket.send(sendPacket);
            }
        }
    }


    private static String getRandomScienceFact() {
        String[] scienceFacts = {
            "Fact 1: Scientists have discovered a hidden energy source in the Earth's core that could power the entire planet for centuries.",
            "Fact 2: A new element, Teslium, has been discovered, which has the ability to transmit electricity wirelessly over vast distances.",
            "Fact 3: Researchers have found a way to manipulate gravity, opening doors to potential levitating transportation systems.",
            "Fact 4: A hidden chamber beneath the Mona Lisa reveals a hidden message from Leonardo da Vinci, predicting futuristic technologies.",
            "Fact 5: An ancient manuscript unveils a formula that turns ordinary metals into gold using the sacred numbers 3, 6, and 9.",
            "Fact 6: Scientists have discovered that the geometry of the universe is based on a mathematical pattern derived from the numbers 3, 6, and 9.",
            "Fact 7: The secret to harnessing unlimited energy is hidden in the relationship between the numbers 3, 6, and 9, as theorized by Nikola Tesla.",
            "Fact 8: A recently discovered alchemical text reveals that Leonardo da Vinci successfully created a philosopher's stone using the power of the numbers 3, 6, and 9.",
            "Fact 9: Astronomers have discovered a celestial object emitting a signal with a pattern based on the numbers 3, 6, and 9, suggesting extraterrestrial intelligence.",
            "Fact 10: The principles of alchemy, when combined with the sacred geometry of the numbers 3, 6, and 9, are said to unlock the secrets of immortality."
        };
    
        Random random = new Random();
        int factIndex = random.nextInt(scienceFacts.length);
    
        return scienceFacts[factIndex];
    }
    
        
    
        private static String getRandomHistoricalEvent() {
        String[] historicalEvents = {
            "Event 1: The discovery of ancient blueprints in Egypt reveals a collaboration between Leonardo da Vinci and Nikola Tesla to create a time machine.",
            "Event 2: An uncovered letter reveals that Leonardo da Vinci and Nikola Tesla secretly met in a hidden underground laboratory to work on teleportation.",
            "Event 3: Nikola Tesla and Leonardo da Vinci were said to have discovered a lost city deep beneath the Earth's surface, where advanced technology thrived.",
            "Event 4: Leonardo da Vinci and Nikola Tesla were rumored to have collaborated on a device that could create limitless, clean energy by harnessing the power of the universe.",
            "Event 5: A secret manuscript from Leonardo da Vinci describes a powerful elixir, capable of transforming base metals into gold and providing eternal youth.",
            "Event 6: Nikola Tesla discovered a cosmic pattern based on the numbers 3, 6, and 9, which could be the key to understanding the true nature of the universe.",
            "Event 7: A lost book on alchemy, co-authored by Leonardo da Vinci and Nikola Tesla, reveals the secret process of harnessing the elements to create new materials.",
            "Event 8: Leonardo da Vinci and Nikola Tesla's experiments with the numbers 3, 6, and 9 led them to invent a machine capable of predicting future events with astonishing accuracy.",
            "Event 9: The hidden knowledge of cosmic alchemy was secretly passed down to Leonardo da Vinci and Nikola Tesla, allowing them to manipulate matter at the quantum level.",
            "Event 10: A mysterious artifact, believed to be designed by Leonardo da Vinci and Nikola Tesla, has the power to control the fundamental forces of the universe using the principles of 3, 6, and 9."
        };
    
        Random random = new Random();
        int eventIndex = random.nextInt(historicalEvents.length);
    
        return historicalEvents[eventIndex];
    }
}
