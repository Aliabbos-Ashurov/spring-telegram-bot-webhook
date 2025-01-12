package com.abbos.financetrackerbot.util.factory;

import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.util.MessageSourceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  16:16
 **/
@Component
@RequiredArgsConstructor
public class ReplyKeyboardMarkupFactory {

    private final MessageSourceUtils messageSourceUtils;

    public ReplyKeyboardMarkup createUserBaseMenuButtons(Language language) {
        return createKeyboardMarkup(language, false, true, "menu.reports", "menu.settings");
    }

    public ReplyKeyboardMarkup createAdminBaseMenuButtons(Language language) {
        return createKeyboardMarkup(language, false, true,
                "menu.admin.base.currency.types",
                "menu.admin.base.transaction.types",
                "menu.admin.base.expense.categories",
                "menu.admin.base.income.categories",
                "menu.admin.base.transaction.statuses",
                "menu.admin.base.note",
                "menu.admin.base.file",
                "menu.admin.base.date",
                "menu.admin.base.clients",
                "menu.admin.base.service.types");
    }

    public ReplyKeyboardMarkup createAdminServiceTypeButtons(Language language) {
        return createKeyboardMarkup(language, true, false,
                "service.type.website",
                "service.type.bot",
                "service.type.smm",
                "service.type.context.ads",
                "service.type.target.ads",
                "service.type.branding",
                "service.type.seo",
                "service.type.other");
    }

    public ReplyKeyboardMarkup createUserMenuReportsButtons(Language language) {
        return createKeyboardMarkup(language, true, false,
                "menu.additional.reports",
                "menu.monthly.expenses",
                "menu.monthly.income");
    }

    public ReplyKeyboardMarkup createUserMenuSettingsButtons(Language language) {
        return createKeyboardMarkup(language, true, false,
                "menu.user.settings.profile.edit",
                "menu.user.settings.access.rights.view",
                "menu.user.settings.password.change",
                "menu.user.settings.language.change");
    }

    public ReplyKeyboardMarkup createChooseLangButtons(Language language) {
        return createKeyboardMarkup(language, true, true,
                "settings.lang.eng",
                "settings.lang.ru",
                "settings.lang.uz");
    }

    public ReplyKeyboardMarkup createBackButton(Language language) {
        return createKeyboardMarkup(language, true, true);
    }

    private ReplyKeyboardMarkup createKeyboardMarkup(Language language, boolean backButton, boolean oneTime,
                                                     String... messageKeys) {
        List<String> messages = Arrays.stream(messageKeys)
                .map(key -> messageSourceUtils.getLocalizedMessage(key, language))
                .collect(Collectors.toList());

        if (backButton) {
            messages.add(messageSourceUtils.getLocalizedMessage("button.back", language));
        }

        List<KeyboardRow> keyboardRows = createKeyboardRows(messages);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(oneTime);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    private static List<KeyboardRow> createKeyboardRows(List<String> messages) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        int totalMessages = messages.size();

        if (totalMessages == 1 || totalMessages == 2) {
            KeyboardRow row = new KeyboardRow();
            row.addAll(messages);
            keyboardRows.add(row);
        } else if (totalMessages % 2 == 0) {
            for (int i = 0; i < totalMessages; i += 2) {
                KeyboardRow row = new KeyboardRow();
                row.add(messages.get(i));
                row.add(messages.get(i + 1));
                keyboardRows.add(row);
            }
        } else {
            for (int i = 0; i < totalMessages - 1; i += 2) {
                KeyboardRow row = new KeyboardRow();
                row.add(messages.get(i));
                row.add(messages.get(i + 1));
                keyboardRows.add(row);
            }
            KeyboardRow lastRow = new KeyboardRow();
            lastRow.add(messages.get(totalMessages - 1));
            keyboardRows.add(lastRow);
        }

        return keyboardRows;
    }
}
