package ru.finance.analyst.telegram.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.{InlineKeyboardButton, KeyboardButton, KeyboardRow}
import ru.finance.analyst.telegram.keyboard.KeyBoards.KeyBoardRow.{KEY_BOARD_CURRENT_ROW, KEY_BOARD_TASK_ROW}

import java.util
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup

object KeyBoards {
  val MAIN_KEY_BOARD: ReplyKeyboardMarkup = {
    val replyKeyboardMarkup          = new ReplyKeyboardMarkup
    val list: util.List[KeyboardRow] = new util.ArrayList[KeyboardRow]
    list.add(KEY_BOARD_CURRENT_ROW)
    list.add(KEY_BOARD_TASK_ROW)
    replyKeyboardMarkup.setKeyboard(list)
    replyKeyboardMarkup
  }

  val TASK_L:ReplyKeyboardMarkup= {
    val replyKeyboardMarkup          = new ReplyKeyboardMarkup
    val list: util.List[KeyboardRow] = new util.ArrayList[KeyboardRow]
    replyKeyboardMarkup.setKeyboard(list)
    replyKeyboardMarkup
  }

   object KeyBoardRow {
     val KEY_BOARD_CURRENT_ROW: KeyboardRow = {
       val keyboardRow: KeyboardRow = new KeyboardRow
       keyboardRow.add(new KeyboardButton("Узнать S&P500"))
       keyboardRow.add(new KeyboardButton("Узнать DJI"))
       keyboardRow.add(new KeyboardButton("Узнать VIX"))
       keyboardRow
     }
     val KEY_BOARD_TASK_ROW: KeyboardRow    = {
       val keyboardRow: KeyboardRow = new KeyboardRow
       keyboardRow.add(new KeyboardButton("CREATE_TASK"))
       keyboardRow.add(new KeyboardButton("DELL_TASK"))
       keyboardRow.add(new KeyboardButton("MY_TASKS"))
       keyboardRow
     }
   }

  object MonitoringTask{
    val BUTTON_DELL_TASK:InlineKeyboardMarkup = {
      val inlineKeyboardMarkup = new InlineKeyboardMarkup()
      val keyboardButtonsRow1: util.List[InlineKeyboardButton] = new util.ArrayList[InlineKeyboardButton]
      val inlineKeyboardButton1 = new InlineKeyboardButton
      inlineKeyboardButton1.setText("DELL")
      inlineKeyboardButton1.setCallbackData("DELETE_TASK")
      keyboardButtonsRow1.add(inlineKeyboardButton1)
      val rowList: util.List[util.List[InlineKeyboardButton]] = new util.ArrayList
      rowList.add(keyboardButtonsRow1)
      inlineKeyboardMarkup.setKeyboard(rowList)
      inlineKeyboardMarkup
    }
  }
}
