package com.staj.fooddelivery;

import com.staj.fooddelivery.entity.*;
import com.staj.fooddelivery.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public void run(String... args) {
        log.info("🚀 Loading seed data...");

        // ── Categories ──────────────────────────────────────────
        Category burger   = categoryRepository.save(Category.builder().name("Burger").build());
        Category pizza    = categoryRepository.save(Category.builder().name("Pizza").build());
        Category doner    = categoryRepository.save(Category.builder().name("Döner").build());
        Category sushi    = categoryRepository.save(Category.builder().name("Sushi").build());
        Category dessert  = categoryRepository.save(Category.builder().name("Tatlı").build());

        // ── Restaurants ─────────────────────────────────────────
        Restaurant burgerKing = restaurantRepository.save(Restaurant.builder()
                .name("Burger King")
                .city("Ankara")
                .minimumOrderAmount(50.0)
                .estimatedDeliveryMinutes(25)
                .deliveryFee(9.99)
                .rating(4.2)
                .category(burger)
                .build());

        Restaurant pizzaHut = restaurantRepository.save(Restaurant.builder()
                .name("Pizza Hut")
                .city("Ankara")
                .minimumOrderAmount(80.0)
                .estimatedDeliveryMinutes(35)
                .deliveryFee(14.99)
                .rating(4.0)
                .category(pizza)
                .build());

        Restaurant donerci = restaurantRepository.save(Restaurant.builder()
                .name("Dönerci Ahmet Usta")
                .city("Ankara")
                .minimumOrderAmount(40.0)
                .estimatedDeliveryMinutes(20)
                .deliveryFee(4.99)
                .rating(4.7)
                .category(doner)
                .build());

        // ── Products ─────────────────────────────────────────────

        // Burger King menu
        productRepository.save(Product.builder().name("Whopper").description("Büyük boy dana burger").price(149.0).section("Burgerler").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Double Whopper").description("Çift katlı dana burger").price(189.0).section("Burgerler").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Chicken Royale").description("Çıtır tavuk burger").price(129.0).section("Burgerler").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Büyük Boy Patates").description("Çıtır kızarmış patates").price(59.0).section("Yanlar").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Soğan Halkası").description("Altın sarısı soğan halkaları").price(49.0).section("Yanlar").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Kola (0.5L)").description("Coca-Cola büyük boy").price(39.0).section("İçecekler").restaurant(burgerKing).build());
        productRepository.save(Product.builder().name("Milkshake Çikolata").description("Kremalı çikolatalı shake").price(69.0).section("İçecekler").restaurant(burgerKing).build());

        // Pizza Hut menu
        productRepository.save(Product.builder().name("Margherita").description("Domates sosu, mozzarella, fesleğen").price(219.0).section("Pizzalar").restaurant(pizzaHut).build());
        productRepository.save(Product.builder().name("Pepperoni").description("Bol pepperoni, mozzarella").price(269.0).section("Pizzalar").restaurant(pizzaHut).build());
        productRepository.save(Product.builder().name("BBQ Chicken").description("Izgara tavuk, BBQ sos, kırmızı soğan").price(289.0).section("Pizzalar").restaurant(pizzaHut).build());
        productRepository.save(Product.builder().name("Dört Peynirli").description("Mozzarella, cheddar, gouda, parmesan").price(309.0).section("Pizzalar").restaurant(pizzaHut).build());
        productRepository.save(Product.builder().name("Sarımsaklı Ekmek").description("Tereyağlı fırın ekmek").price(69.0).section("Başlangıçlar").restaurant(pizzaHut).build());
        productRepository.save(Product.builder().name("Tiramisu").description("İtalyan klasiği tatlı").price(99.0).section("Tatlılar").restaurant(pizzaHut).build());

        // Dönerci menu
        productRepository.save(Product.builder().name("Döner Dürüm").description("Tavuk veya dana, lavaş içinde").price(89.0).section("Dürümler").restaurant(donerci).build());
        productRepository.save(Product.builder().name("Döner Tabak").description("Pilav üzeri döner, cacık ile").price(129.0).section("Tabaklar").restaurant(donerci).build());
        productRepository.save(Product.builder().name("Yarım Ekmek Döner").description("Taze ekmek içinde döner").price(69.0).section("Ekmek Arası").restaurant(donerci).build());
        productRepository.save(Product.builder().name("Ayran").description("Soğuk ev yapımı ayran").price(19.0).section("İçecekler").restaurant(donerci).build());

        // ── Users ─────────────────────────────────────────────────
        User ahmet = userRepository.save(User.builder()
                .fullName("Ahmet Yılmaz")
                .email("ahmet@example.com")
                .phone("0532 111 22 33")
                .build());

        User zeynep = userRepository.save(User.builder()
                .fullName("Zeynep Kaya")
                .email("zeynep@example.com")
                .phone("0541 444 55 66")
                .build());

        // ── Addresses ─────────────────────────────────────────────
        addressRepository.save(Address.builder()
                .title("Ev")
                .city("Ankara")
                .district("Çankaya")
                .street("Atatürk Bulvarı")
                .buildingNo("42")
                .apartmentNo("7")
                .directions("Yeşil kapılı bina, interkom 7")
                .user(ahmet)
                .build());

        addressRepository.save(Address.builder()
                .title("İş")
                .city("Ankara")
                .district("Kızılay")
                .street("Ziya Gökalp Caddesi")
                .buildingNo("15")
                .apartmentNo("301")
                .user(ahmet)
                .build());

        addressRepository.save(Address.builder()
                .title("Ev")
                .city("Ankara")
                .district("Keçiören")
                .street("Plevne Caddesi")
                .buildingNo("8")
                .apartmentNo("3")
                .user(zeynep)
                .build());

        log.info("✅ Seed data loaded — {} categories, {} restaurants, {} products, {} users",
                categoryRepository.count(),
                restaurantRepository.count(),
                productRepository.count(),
                userRepository.count());
    }
}
