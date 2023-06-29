package engine.entities

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "BOOK")
open class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", nullable = false)
    open var id: Int? = null

    @Size(max = 50)
    @Column(name = "TITLE", length = 50)
    open var title: String? = null

    @Size(max = 30)
    @Column(name = "AUTHOR", length = 30)
    open var author: String? = null

    @Column(name = "PRICE", precision = 8, scale = 2)
    open var price: BigDecimal? = null

    @Column(name = "AMOUNT")
    open var amount: Int? = null
}