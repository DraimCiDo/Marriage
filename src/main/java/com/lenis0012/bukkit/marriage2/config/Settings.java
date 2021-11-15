package com.lenis0012.bukkit.marriage2.config;

import com.lenis0012.pluginutils.modules.configuration.mapping.ConfigHeader;
import com.lenis0012.pluginutils.modules.configuration.mapping.ConfigOption;

import java.util.Arrays;
import java.util.List;

public class Settings {
    /**
     * Uncatagorized ConfigOption
     */
    public static final ConfigOption<Integer> REQUEST_EXPIRY = new ConfigOption<>("requestExpiry", 60);
    public static final ConfigOption<Boolean> ENABLE_PRIEST = new ConfigOption<>("enable-priests", false);
    @ConfigHeader("Разрешите несколько раз менять пол игрока.")
    public static final ConfigOption<Boolean> ALLOW_GENDER_CHANGE = new ConfigOption<>("enable-gender-change", true);

    /**
     * Cooldown
     */
    public static final ConfigOption<Integer> COOLDOWN_KISS = new ConfigOption<>("cooldown.kiss", 2);

    /**
     * Features
     */
    @ConfigHeader("Показывать цвета в списке женатых.")
    public static final ConfigOption<Boolean> GENDER_IN_LIST = new ConfigOption<>("features.genders-in-list", true);

    /**
     * Chat
     */
    @ConfigHeader(path = "chat", value = {
            "Это чат, установите формат личных сообщений и статус в чате.",
            "Поддерживаемые теги для чата: {partner}, для лс: {name}, {message}, для слежки: {sender}, {receiver}, {message}",
            "Доступные значки: {icon:heart}, {icon:male}, {icon:female}, {icon:genderless}",
            "Если вы используете пользовательский плагин чата, поместите {marriage_status} в формат и установите force-status-format & force-gender-format на false",
            "Чтобы показать пол в чате, поставьте {marriage_gender} в формата вашего чата"
    })
    public static final ConfigOption<String> PM_FORMAT = new ConfigOption<>("chat.pm-format", "&4{icon:heart}&c{name}&4{icon:heart} &7{message}");
    public static final ConfigOption<String> CHAT_FORMAT = new ConfigOption<>("chat.status-format", "&4&l♥ &r");
    public static final ConfigOption<String> CHAT_FORMAT_UNMARRIED = new ConfigOption<>("chat.unmarried-status-format", "");
    public static final ConfigOption<String> CHATSPY_FORMAT = new ConfigOption<>("chat.spy-format", "&c[CHAT SPY] &7{sender} -> {receiver}&f: {message}");
    public static final ConfigOption<Boolean> FORCE_FORMAT = new ConfigOption<>("chat.force-status-format", false);
    public static final ConfigOption<Boolean> FORCE_GENDER_FORMAT = new ConfigOption<>("chat.force-gender-format", false);
    public static final ConfigOption<String> PREFIX_MALE = new ConfigOption<>("chat.male-prefix", "&b{icon:male} &r");
    public static final ConfigOption<String> PREFIX_FEMALE = new ConfigOption<>("chat.female-prefix", "&d{icon:female} &r");
    public static final ConfigOption<String> PREFIX_GENDERLESS = new ConfigOption<>("chat.genderless-prefix", "");

    /**
     * Kissing
     */
    @ConfigHeader(path = "kisses", value = {
            "Поцелуи, показывают сердечки, когда 2 женатых игрока целуют друг друга.",
            "Количество сердец - это случайное число от минимального до максимального."
    })
    public static final ConfigOption<Boolean> KISSES_ENABLED = new ConfigOption<>("kisses.enabled", true);
    public static final ConfigOption<Integer> KISSES_AMOUNT_MIN = new ConfigOption<>("kisses.amount-min", 5);
    public static final ConfigOption<Integer> KISSES_AMOUNT_MAX = new ConfigOption<>("kisses.amount-max", 10);

    /**
     * XP-Boost
     */
    @ConfigHeader(path = "exp-boost", value = "Увеличение опыта, которые получают игроки, когда они находятся рядом друг с другом.")
    public static final ConfigOption<Boolean> EXP_BOOST_ENABLED = new ConfigOption<>("exp-boost.enabled", true);
    public static final ConfigOption<Double> EXP_BOOST_DISTANCE = new ConfigOption<>("exp-boost.distance", 50.0);
    public static final ConfigOption<Double> EXP_BOOST_MULTIPLIER = new ConfigOption<>("exp-boost.multiplier", 2.0);
    @ConfigHeader("Сумма бонуса, увеличивающего опыт, установите значение false, чтобы отключить.")
    public static final ConfigOption<Boolean> EXP_BOOST_ANNOUNCE = new ConfigOption<>("exp-boost.announce", true);

    /**
     * Support
     */
    @ConfigHeader("Подхват Plotsqueard.")
    public static final ConfigOption<Boolean> PLOTSQUARED_AUTO_TRUST = new ConfigOption<>("support.plotsquared-auto-trust", true);

    /**
     * Economy
     */
    @ConfigHeader({"Economy settings, uses Vault.", "enable 'show-on-help' to show prices in help command."})
    public static final ConfigOption<Boolean> ECONOMY_ENABLED = new ConfigOption<>("economy.enabled", false);
    public static final ConfigOption<Boolean> ECONOMY_SHOW_PRICE = new ConfigOption<>("economy.show-on-help", true);
    public static final ConfigOption<Double> PRICE_MARRY = new ConfigOption<>("economy.marriage-price", 100.0);
    public static final ConfigOption<Double> PRICE_TELEPORT = new ConfigOption<>("economy.teleport-price", 0.0);
    public static final ConfigOption<Double> PRICE_SETHOME = new ConfigOption<>("economy.sethome-price", 0.0);
    public static final ConfigOption<Double> PRICE_HEAL = new ConfigOption<>("economy.heal-price", 0.0);
    public static final ConfigOption<Double> PRICE_DIVORCE = new ConfigOption<>("economy.divorce-price", 0.0);

    /**
     * Updater
     */
    @ConfigHeader(path = "updater", value = {
            "Updater settings, checks for updates. We recommend to keep this enabled.",
            "Available channels: RELEASE, BETA, ALPHA"
    })
    public static final ConfigOption<Boolean> ENABLE_UPDATE_CHECKER = new ConfigOption<>("updater.enabled", true);
    public static final ConfigOption<Boolean> ENABLE_CHANGELOG = new ConfigOption<>("updater.changelog", true);
    public static final ConfigOption<String> UPDATER_CHANNEL = new ConfigOption<>("updater.channel", "BETA");

    /**
     * Lists
     */
    @ConfigHeader("List of commands that no one can use, for instance 'gift'.")
    public static final ConfigOption<List<String>> DISABLED_COMMANDS = new ConfigOption<>("disabled-commands", Arrays.asList("command1", "command2"));
}