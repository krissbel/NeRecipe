import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.db.entity.StepEntity

internal fun StepEntity.toModel() = Step(
    id = id,
    recipeId = recipeId,
    stepText = stepText
)

internal fun Step.toEntity() = StepEntity(
    id = id,
    recipeId = recipeId,
    stepText = stepText
)