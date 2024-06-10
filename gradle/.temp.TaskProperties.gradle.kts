
public sealed class TaskProperties {
    public abstract val taskName: String?
    public abstract val targetClassName: String
    public companion object {
        public fun create(
            taskName: String?,
            targetClassName: String
        ): TaskProperties {
            require( targetClassName.isNotBlank() ) {
                "::: targetClasssName field must not be 'null' nor empty."
            }
            return TaskPropertiesImpl( taskName, targetClassName );
        }
    }
    private data class TaskPropertiesImpl(
        public override val taskName: String?,
        public override val targetClassName: String
    ): TaskProperties();
}