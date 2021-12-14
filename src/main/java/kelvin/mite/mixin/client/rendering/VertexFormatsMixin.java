package kelvin.mite.mixin.client.rendering;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.VertexFormats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VertexFormats.class)
public class VertexFormatsMixin {
    @Shadow
    public static VertexFormat POSITION_COLOR_TEXTURE_LIGHT_NORMAL;
    @Shadow
    public static VertexFormat POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL;

    @Shadow
    public static VertexFormatElement POSITION_ELEMENT;
    @Shadow
    public static VertexFormatElement COLOR_ELEMENT;
    @Shadow
    public static VertexFormatElement TEXTURE_0_ELEMENT;
    @Shadow
    public static VertexFormatElement OVERLAY_ELEMENT;
    @Shadow
    public static VertexFormatElement LIGHT_ELEMENT;
    @Shadow
    public static VertexFormatElement NORMAL_ELEMENT;
    @Shadow
    public static VertexFormatElement PADDING_ELEMENT;
    @Shadow
    public static VertexFormatElement TEXTURE_ELEMENT;

    private static VertexFormatElement FLAGS_ELEMENT;

    static {
        POSITION_ELEMENT = new VertexFormatElement(0, VertexFormatElement.DataType.FLOAT, VertexFormatElement.Type.POSITION, 3);
        COLOR_ELEMENT = new VertexFormatElement(0, VertexFormatElement.DataType.UBYTE, VertexFormatElement.Type.COLOR, 4);
        TEXTURE_0_ELEMENT = new VertexFormatElement(0, VertexFormatElement.DataType.FLOAT, VertexFormatElement.Type.UV, 2);
        OVERLAY_ELEMENT = new VertexFormatElement(1, VertexFormatElement.DataType.SHORT, VertexFormatElement.Type.UV, 2);
        LIGHT_ELEMENT = new VertexFormatElement(2, VertexFormatElement.DataType.SHORT, VertexFormatElement.Type.UV, 2);
        NORMAL_ELEMENT = new VertexFormatElement(0, VertexFormatElement.DataType.BYTE, VertexFormatElement.Type.NORMAL, 3);
        FLAGS_ELEMENT = new VertexFormatElement(0, VertexFormatElement.DataType.FLOAT, VertexFormatElement.Type.GENERIC, 1);

        POSITION_COLOR_TEXTURE_LIGHT_NORMAL = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder().put("Position", POSITION_ELEMENT).put("Color", COLOR_ELEMENT).put("UV0", TEXTURE_0_ELEMENT).put("UV2", LIGHT_ELEMENT).put("Normal", NORMAL_ELEMENT).put("Flags", FLAGS_ELEMENT).put("Padding", PADDING_ELEMENT).build());
        //POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder().put("Position", POSITION_ELEMENT).put("Color", COLOR_ELEMENT).put("UV0", TEXTURE_0_ELEMENT).put("UV1", OVERLAY_ELEMENT).put("UV2", LIGHT_ELEMENT).put("Normal", NORMAL_ELEMENT).put("Flags", FLAGS_ELEMENT).put("Padding", PADDING_ELEMENT).build());

    }
}
