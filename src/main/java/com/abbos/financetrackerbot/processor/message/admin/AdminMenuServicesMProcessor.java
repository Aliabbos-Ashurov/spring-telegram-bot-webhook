package com.abbos.financetrackerbot.processor.message.admin;

import com.abbos.financetrackerbot.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.processor.Processor;
import com.abbos.financetrackerbot.processor.TelegramBotHandler;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.state.AdminBaseMenuState;
import com.abbos.financetrackerbot.util.MessageSourceUtils;
import com.abbos.financetrackerbot.util.factory.SendMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.abbos.financetrackerbot.state.AdminBaseMenuState.*;
import static com.abbos.financetrackerbot.state.UserBaseMenuState.USER_ON_BASE_MENU;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  19:42
 **/
@Component
@RequiredArgsConstructor
public class AdminMenuServicesMProcessor implements TelegramBotHandler, Processor {

    private final UserService userService;
    private final MessageSourceUtils messageSourceUtils;
    private final SendMessageFactory sendMessageFactory;

    @Override
    public BotApiMethod<?> process(Update update, String state) {
        final var message = update.getMessage();
        final var chatId = message.getFrom().getId();
        final var text = message.getText();
        final var user = userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
        final var language = user.getLanguage();
        return switch (user.getState()) {
            case ADMIN_BASE_MENU_STATE_SERVICE_TYPES -> handleStates(text, user, chatId, language);
            case ADMIN_MENU_SERVICE_WEBSITE,
                 ADMIN_MENU_SERVICE_BOT,
                 ADMIN_MENU_SERVICE_SMM,
                 ADMIN_MENU_SERVICE_CONTEXT_ADS,
                 ADMIN_MENU_SERVICE_TARGET_ADS,
                 ADMIN_MENU_SERVICE_BRANDING,
                 ADMIN_MENU_SERVICE_SEO,
                 ADMIN_MENU_SERVICE_OTHER -> redirectToBack(chatId, user);
            default -> unsupported(chatId, language);
        };
    }

    private BotApiMethod<?> handleStates(String text, User user, Long chatId, Language language) {
        if (isActionBack(text, language)) {
            return redirectToBaseMenu(chatId, user);
        } else if (checkLocalizedMessage("service.type.website", text, language)) {
            return handleServiceWebsite(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.bot", text, language)) {
            return handleServiceBot(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.smm", text, language)) {
            return handleServiceSMM(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.context.ads", text, language)) {
            return handleServiceContextAds(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.target.ads", text, language)) {
            return handleServiceTargetAds(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.branding", text, language)) {
            return handleServiceBranding(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.seo", text, language)) {
            return handleServiceSeo(user, chatId, language);
        } else if (checkLocalizedMessage("service.type.other", text, language)) {
            return handleServiceOther(user, chatId, language);
        }
        return unsupported(chatId, language);
    }

    private BotApiMethod<?> handleServiceWebsite(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_WEBSITE);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceBot(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_BOT);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceSMM(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_SMM);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceContextAds(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_CONTEXT_ADS);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceTargetAds(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_TARGET_ADS);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceBranding(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_BRANDING);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceSeo(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_SEO);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceOther(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_MENU_SERVICE_OTHER);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    @Override
    public boolean isActionBack(String text, Language language) {
        return checkLocalizedMessage("button.back", text, language);
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
    }

    @Override
    public Language getUserLanguage(Long chatId) {
        return getUserByChatId(chatId).getLanguage();
    }

    @Override
    public void updateUserState(User user, String state) {
        user.setState(state);
        userService.update(user);
    }

    @Override
    public boolean checkLocalizedMessage(String key, String message, Language language) {
        final var localizedMessage = messageSourceUtils.getLocalizedMessage(key, language);
        return localizedMessage.equals(message);
    }

    @Override
    public SendMessage unsupported(Long chatID, Language language) {
        User user = getUserByChatId(chatID);
        Role role = user.getRole();
        final var state = role.equals(Role.USER) ? USER_ON_BASE_MENU : AdminBaseMenuState.ADMIN_ON_BASE_MENU;
        updateUserState(user, state);
        return role.equals(Role.USER)
                ? sendMessageFactory.sendMessageUserMenu(String.valueOf(chatID), language)
                : sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatID), language);
    }

    @Override
    public BotApiMethod<?> redirectToBaseMenu(Long chatId, User user) {
        updateUserState(user, ADMIN_ON_BASE_MENU);
        return sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatId), user.getLanguage());
    }

    @Override
    public BotApiMethod<?> redirectToBack(Long chatId, User user) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_SERVICE_TYPES);
        return sendMessageFactory.sendMessageAdminServiceType(String.valueOf(chatId), user.getLanguage());
    }
}
