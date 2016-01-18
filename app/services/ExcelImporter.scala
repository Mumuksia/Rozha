package services

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.WorkbookFactory
import scala.collection.immutable.Vector
import java.nio.channels.Channel
import java.nio.channels.Channels
import java.io.FileOutputStream
import java.io.File
import scala.xml.PrettyPrinter
import scala.xml.Elem
import scala.xml.Null
import scala.xml.TopScope
import scala.xml.Text
import java.nio.charset.Charset

class ExcelImporter {
  
    def parse(path: String) = {
        IOUtils.readFile(path) { input =>
            val sheet = WorkbookFactory.create(input).getSheet("Clan_Members")

            val headRow = sheet.getRow(0)
            val colName = (headRow.getFirstCellNum() until headRow.getLastCellNum()).map { index =>
                headRow.getCell(index).getStringCellValue()
            }

            val data = (sheet.getFirstRowNum() + 1 until sheet.getLastRowNum()).map { index =>
                val row = sheet.getRow(index)
                (row.getFirstCellNum() until row.getLastCellNum()).map { index =>
                    val cell = row.getCell(index)

                    var ret = ""
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING)
                        ret = cell.getStringCellValue()
                    }
                    ret
                }
            }

            (data)
        }.asInstanceOf[(Vector[Vector[String]])]
    }

    def writeXML(path: String, name: String, data: (Vector[String], Vector[Vector[String]])) {
        IOUtils.writeXML(path) { writer =>
            <DICT name={ name }>
                {
                    data._2.map { dt =>
                        <TR>
                            {
                                dt.zipWithIndex.map {
                                    case (e, i) =>
                                        Elem(null, data._1(i), Null, TopScope, Text(e))
                                }
                            }
                        </TR>
                    }
                }
            </DICT>
        }

    }

}
