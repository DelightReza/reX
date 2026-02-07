package androidx.camera.core.imagecapture;

import android.util.Size;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.imagecapture.CaptureNode;
import androidx.camera.core.processing.Edge;
import java.util.List;

/* loaded from: classes3.dex */
final class AutoValue_CaptureNode_In extends CaptureNode.AbstractC0179In {
    private final Edge errorEdge;
    private final int inputFormat;
    private final List outputFormats;
    private final PostviewSettings postviewSettings;
    private final Edge requestEdge;
    private final Size size;
    private final boolean virtualCamera;

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    ImageReaderProxyProvider getImageReaderProxyProvider() {
        return null;
    }

    AutoValue_CaptureNode_In(Size size, int i, List list, boolean z, ImageReaderProxyProvider imageReaderProxyProvider, PostviewSettings postviewSettings, Edge edge, Edge edge2) {
        if (size == null) {
            throw new NullPointerException("Null size");
        }
        this.size = size;
        this.inputFormat = i;
        if (list == null) {
            throw new NullPointerException("Null outputFormats");
        }
        this.outputFormats = list;
        this.virtualCamera = z;
        this.postviewSettings = postviewSettings;
        if (edge == null) {
            throw new NullPointerException("Null requestEdge");
        }
        this.requestEdge = edge;
        if (edge2 == null) {
            throw new NullPointerException("Null errorEdge");
        }
        this.errorEdge = edge2;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    Size getSize() {
        return this.size;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    int getInputFormat() {
        return this.inputFormat;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    List getOutputFormats() {
        return this.outputFormats;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    boolean isVirtualCamera() {
        return this.virtualCamera;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    PostviewSettings getPostviewSettings() {
        return this.postviewSettings;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    Edge getRequestEdge() {
        return this.requestEdge;
    }

    @Override // androidx.camera.core.imagecapture.CaptureNode.AbstractC0179In
    Edge getErrorEdge() {
        return this.errorEdge;
    }

    public String toString() {
        return "In{size=" + this.size + ", inputFormat=" + this.inputFormat + ", outputFormats=" + this.outputFormats + ", virtualCamera=" + this.virtualCamera + ", imageReaderProxyProvider=" + ((Object) null) + ", postviewSettings=" + this.postviewSettings + ", requestEdge=" + this.requestEdge + ", errorEdge=" + this.errorEdge + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CaptureNode.AbstractC0179In) {
            CaptureNode.AbstractC0179In abstractC0179In = (CaptureNode.AbstractC0179In) obj;
            if (this.size.equals(abstractC0179In.getSize()) && this.inputFormat == abstractC0179In.getInputFormat() && this.outputFormats.equals(abstractC0179In.getOutputFormats()) && this.virtualCamera == abstractC0179In.isVirtualCamera()) {
                abstractC0179In.getImageReaderProxyProvider();
                abstractC0179In.getPostviewSettings();
                if (this.requestEdge.equals(abstractC0179In.getRequestEdge()) && this.errorEdge.equals(abstractC0179In.getErrorEdge())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((this.size.hashCode() ^ 1000003) * 1000003) ^ this.inputFormat) * 1000003) ^ this.outputFormats.hashCode()) * 1000003) ^ (this.virtualCamera ? 1231 : 1237)) * (-721379959)) ^ 0) * 1000003) ^ this.requestEdge.hashCode()) * 1000003) ^ this.errorEdge.hashCode();
    }
}
