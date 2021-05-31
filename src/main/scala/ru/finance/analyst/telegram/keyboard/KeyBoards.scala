package ru.finance.analyst.telegram.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.{KeyboardButton, KeyboardRow}
import ru.finance.analyst.telegram.keyboard.KeyBoards.KeyBoardRow.{KEY_BOARD_CURRENT_ROW, KEY_BOARD_TASK_ROW}

import java.util

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


}
