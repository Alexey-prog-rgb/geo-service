package ru.netology.sender;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageSenderImplTest {
    @Mock
    private GeoService geoService;

    @Mock
    private LocalizationService localizationService;

    @AfterEach
    public void reset() {
        System.out.println();
        System.out.println();
    }

    @Test
    public void sendRuMessage() {
        System.out.println("send russian message");
        // arrange
        Map<String, String> headers = new HashMap<>(); //
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);
        String expected = "Добро пожаловать";

        Mockito.when(geoService.byIp(any(String.class))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender sender = new MessageSenderImpl(geoService, localizationService);

        // act
        String actual = sender.send(headers);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sendEngMessage() {
        System.out.println("send english message");
        // arrange
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);
        String expected = "Welcome";

        Mockito.when(geoService.byIp(any(String.class))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        // act
        MessageSender sender = new MessageSenderImpl(geoService, localizationService);
        String actual = sender.send(headers);

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ByIpTest(){
        System.out.println("byIpTest");
        // arrange
        GeoServiceImpl gs = new GeoServiceImpl();
        String ip = GeoServiceImpl.MOSCOW_IP;
        String expected = "Moscow";

        // act
        String actual = gs.byIp(ip).getCity();

        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void LocalizationServiceTest(){
        System.out.println("localizationServiceTest");
        // arrange
        LocalizationServiceImpl ls = new LocalizationServiceImpl();
        String expected = "Добро пожаловать";

        // act
        String actual = ls.locale(Country.RUSSIA);

        // assert
        Assertions.assertEquals(expected, actual);
    }

}
