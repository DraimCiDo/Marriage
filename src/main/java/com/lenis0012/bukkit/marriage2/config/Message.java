package com.lenis0012.bukkit.marriage2.config;

import com.lenis0012.bukkit.marriage2.Marriage;
import com.lenis0012.bukkit.marriage2.misc.BConfig;
import com.lenis0012.bukkit.marriage2.utils.ColorUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public enum Message {
    PLAYER_NOT_FOUND("&cИмя игрока %s не найдено!"),
    TARGET_ALREADY_MARRIED("&cИгрок %s уже замужем за кем-то!"),
    ALREADY_MARRIED("&cТы уже замужем за кем-то!"),
    MARRIED("&a&lИгроки %s и %s только что поженились!"),
    MARRIAGE_REQUESTED("&aИгрок %s попросил вас вступить с ним в брак, используйте &e/marry %s &aчтобы принят предложение."),
    REQUEST_SENT("&aВы сделали предложение %s!"),
    NOT_MARRIED("&cВ настоящее время вы не состоите в браке с кем-либо!"),
    DIVORCED("&aИгроки %s и %s расстались!"),
    HOME_TELEPORT("&aТелепортация в дом семьи!"),
    HOME_NOT_SET("&cВ настоящее время у вас нет установленного семейного дома!"),
    NO_ITEM("&cВы не держите в руках предмет для подарка!"),
    ITEM_GIFTED("&aВы дали %s своему партнеру!"),
    GIFT_RECEIVED("&aВы получили %s в подарок от вашего партнера!"),
    PARTNER_NOT_ONLINE("&cВаш партнер в настоящее время не находится в сети!"),
    FETCHING_LIST("&eПолучение списка браков игроков..."),
    HOME_SET("&aВы создали дом для семьи!"),
    INVALID_FORMAT("'&cАргумент не может быть проанализирован до целого числа!"),
    INVALID_GENDER("&cЭто не допустимый пол! Выберите \"мужчина\" или \"женщина\""),
    GENDER_SET("&aВаш пол был установлен на %s!"),
    MARRY_SELF("&cВы не можете жениться на себе!"),
    NEGATIVE_NUMBER("&cВы должны ввести положительное число!"),
    PRIEST_ADDED("&aНазначьте игрока священником, теперь он может жениться на других игроках!"),
    PRIEST_REMOVED("&aОтмененный игрок как священник, теперь он больше не может жениться на других игроках!"),
    TELEPORTED("&aВы были телепортированы в локацию вашего партнера!"),
    TELEPORTED_2("&aВаш партнер только что телепортировался к вам!"),
    ONLINE_SINCE("&aВаш партнер был &2Онлайн &a%s!"),
    OFFLINE_SINCE("&aВаш партнер &cОффлайн &a%s!"),
    NOT_A_PRIEST("&cВам не разрешается жениться на двух игроках!"),
    COOLDOWN("&cВы не можете выполнять это действие слишком часто!"),
    UPDATE_AVAILABLE("&f&l[Marriage] &eДоступно новое обновление! %s для %s\nПропиши &6/marry update &eдля обновления."),
    PAID_FEE("&a%s снято с вашего баланса"),
    INSUFFICIENT_MONEY("&cУ вас недостаточно средств, требуется %s"),
    PARTNER_FEE("&cВаш партнер не смог заплатить брачный взнос!"),
    MARRIED_TO("&fженат на %s"),
    CHAT_ENABLED("&aВы находитесь в режиме брачного чата!"),
    CHAT_DISABLED("&aВы больше не находитесь в режиме брачного чата!"),
    CHAT_SPY_ENABLED("&aВы начали шпионаж за брачным чатом!"),
    CHAT_SPY_DISABLED("&aВы больше не шпионите за брачным чатом!"),
    NO_HEALTH("&cУ вас не хватает здоровья, чтобы делиться им!"),
    FULL_HEALTH("&cВаш партнер уже полностью здоров!"),
    HEALTH_GIVEN("&aТы исцелил своего партнера на %s сердец!"),
    HEALTH_TAKEN("&aВы были исцелены вашим партнером на %s сердец!"),
    PVP_ENABLED("&aВы включили ПвП со своим партнером!"),
    PVP_DISABLED("&aВы отключили ПвП со своим партнером!"),
    PARTNER_PVP("&aВаш партнер изменил правила ПвП."),
    BONUS_EXP("&aВы получили %s дополнительного опыта за прокачку с вашим партнером!"),
    CONFIG_RELOAD("&aПараметры конфигурации были перезагружены, пожалуйста, обратите внимание, что некоторые настройки могут не применяться до рестарта."),
    GENDER_ALREADY_CHANGED("&cВы уже указали свой пол, вы можете сделать это только один раз."),
    TELEPORT_UNSAFE("&cМестоположение, в которое вы пытаетесь телепортироваться, небезопасно или затруднено."),
    PARTNER_INVENTORY_FULL("&cИнвентарь вашего партнера полон!"),

    // COMMANDS
    COMMAND_MARRY(ColorUtils.colorMessage("{#7F7FD5>}Отравить предложение брака игроку{#91EAE4<}")),
    COMMAND_MARRY_PRIEST(ColorUtils.colorMessage("{#7F7FD5<}Женить двух игроков друг на друге{#91EAE4<}")),
    COMMAND_CHAT(ColorUtils.colorMessage("{#7F7FD5<}Включить режим чата только для партнеров{#91EAE4<}")),
    COMMAND_DIVORCE(ColorUtils.colorMessage("{#7F7FD5<}Развестись со своим нынешним партнером{#91EAE4<}")),
    COMMAND_GENDER(ColorUtils.colorMessage("{#7F7FD5<}Указать свой пол{#91EAE4<}")),
    COMMAND_GIFT(ColorUtils.colorMessage("{#7F7FD5<}Подарите предмет, который вы в настоящее время держите{#91EAE4<}")),
    COMMAND_HOME(ColorUtils.colorMessage("{#7F7FD5<}Телепортация в семейный дом{#91EAE4<}")),
    COMMAND_LIST(ColorUtils.colorMessage("{#7F7FD5<}Просмотреть список всех женатых игроков{#91EAE4<}")),
    COMMAND_SEEN(ColorUtils.colorMessage("{#7F7FD5<}Проверить, когда ваш партнер в последний раз входил в систему{#91EAE4<}")),
    COMMAND_SETHOME(ColorUtils.colorMessage("{#7F7FD5<}Создать дом для себя и своего партнера{#91EAE4<}")),
    COMMAND_TELEPORT(ColorUtils.colorMessage("{#7F7FD5<}Телепортироваться к своему партнеру{#91EAE4<}")),
    COMMAND_HEAL(ColorUtils.colorMessage("{#7F7FD5<}Передать свое здоровье вашему партнеру{#91EAE4<}")),
    COMMAND_PVP(ColorUtils.colorMessage("{#7F7FD5<}Включить/отключить ПвП с вашим партнером{#91EAE4<}")),

    // WORDS
    STATUS(ColorUtils.colorMessage("{#8E2DE2<}Статус:{#4A00E0<} %s")),
    SINGLE(ColorUtils.colorMessage("{#7F7FD5<}одинок{#91EAE4<}")),
    ON_OFF(ColorUtils.colorMessage("{#7F7FD5<}вкл/выкл{#91EAE4<}"));

    private final String defaultMessage;
    private String message;

    Message(String def) {
        this.defaultMessage = def;
        this.message = def; // Use default if not loaded yet
    }

    private void reload(BConfig config) {
        this.message = config.getOrSet(name().toLowerCase(), defaultMessage);
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void send(Player player, Object... params) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message, params)));
    }

    public static void reloadAll(Marriage marriage) {
        BConfig config = marriage.getBukkitConfig("messages.yml");
        for(Message message : values()) {
            message.reload(config);
        }

        config.save();
    }
}
